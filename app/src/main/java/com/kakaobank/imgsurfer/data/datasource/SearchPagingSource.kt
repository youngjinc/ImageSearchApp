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

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            val page = params.key ?: 1
            var imageList: List<Content> = emptyList()
            var videoList: List<Content> = emptyList()

            KakaoLog.d(keyword)
            if (!isEndImage) {
                searchService.searchImage(keyword = keyword, page = page, size = 20)
                    .let { response ->
                        isEndImage = response.meta.isEnd
                        imageList = response.documents.map { it.toContentList() }
                    }
            }
            if (!isEndVideo) {
                searchService.searchVideo(keyword = keyword, page = page, size = 20)
                    .let { response ->
                        isEndVideo = response.meta.isEnd
                        videoList = response.documents.map { it.toContentList() }
                    }
            }

            val contentList = (imageList + videoList).sortedByDescending { it.dateTime }
            val prevKey = if (page == 1) null else page - 1
            val nextKey =
                if (contentList.isEmpty() || (isEndImage && isEndVideo)) null else page + 1

            LoadResult.Page(
                data = contentList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
}
