package com.kakaobank.imgsurfer.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kakaobank.imgsurfer.data.service.SearchService
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.util.KakaoLog
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(private val searchService: SearchService, private val keyword: String) :
    PagingSource<Int, Content>() {
    private var isEndImage: Boolean = false
    private var isEndVideo: Boolean = false
    private var isEndTemp: Boolean = false
    private var tempContents = mutableListOf<Content>()
    lateinit var imageList: List<Content>
    lateinit var videoList: List<Content>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            imageList = emptyList()
            videoList = emptyList()

            val page = params.key ?: 1

            if (!isEndImage) searchImage(page)
            if (!isEndVideo) searchVideo(page)

            val currentContents = getLatestContentByPageSize()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (isEndTemp) null else page + 1

            LoadResult.Page(
                data = currentContents,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private suspend fun searchImage(page: Int) {
        runCatching {
            searchService.searchImage(keyword = keyword, page = page, size = PAGE_SIZE)
        }.onSuccess { response ->
            isEndImage = response.meta.isEnd
            imageList = response.documents.map { it.toContentList() }
        }.onFailure {
            isEndImage = true
            imageList = emptyList()
            it.message?.let { KakaoLog.e(it) }
        }
    }

    private suspend fun searchVideo(page: Int) {
        runCatching {
            searchService.searchVideo(keyword = keyword, page = page, size = PAGE_SIZE)
        }.onSuccess { response ->
            isEndVideo = response.meta.isEnd
            videoList = response.documents.map { it.toContentList() }
        }.onFailure {
            isEndVideo = true
            videoList = emptyList()
            it.message?.let { KakaoLog.e(it) }
        }
    }

    /** 페이지 사이즈 수만큼의 최신 콘텐츠 리스트를 반환하는 함수.
     * 이미지(30), 비디오(30)으로 총 콘텐츠 정보 60개를 불러왔을 때 최신순 정렬을 적용하여 30개를 PagingData로 내보내고,
     * 나머지 콘텐츠는 임시 저장소에 저장해두고 다음 페이지에 대한 이미지, 비디오 검색결과를 합해서 위 과정을 반복하여 최신순으로 두 리스트를 정렬한다.
     * 이미지(30) 중 가장 최신 이미지가, 비디오(30) 중 오래된 이미지보다 더 오래전이라면 비디오(30)을 PagingData로 내보내기 위함.*/
    private fun getLatestContentByPageSize(): List<Content> {
        // 이미지와 동영상 중 어느 하나라도 검색 결과가 존재한다면 임시 저장소에 추가하고, 임시 저장소(tempContents)의 요소를 최신순으로 정렬
        if (imageList.isNotEmpty() || videoList.isNotEmpty()) {
            tempContents.addAll(imageList + videoList)
            tempContents.sortByDescending { it.dateTime }
        }

        // 현재 PagingData로 내보낼 최신 콘텐츠로, 갯수는 페이지 사이즈에 해당
        val currentContents =
            if (tempContents.size >= PAGE_SIZE) {
                tempContents.subList(0, PAGE_SIZE - 1)
            } else {
                isEndTemp = true
                tempContents
            }

        // 내보낼 최신 콘텐츠를 기존의 임시 저장소에서 제거
        tempContents =
            if (currentContents.size == PAGE_SIZE)
                tempContents.subList(PAGE_SIZE, tempContents.size - 1)
            else mutableListOf()

        return currentContents
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    companion object {
        private const val PAGE_SIZE = 30
    }
}
