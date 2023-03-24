package com.kakaobank.imgsurfer.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kakaobank.imgsurfer.data.service.SearchService
import com.kakaobank.imgsurfer.domain.model.Content
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(private val searchService: SearchService, private val keyword: String) :
    PagingSource<Int, Content>() {
    private var isEndImage: Boolean = false
    private var isEndVideo: Boolean = false
    private var isEndTemp: Boolean = false
    private var tempContents = mutableListOf<Content>()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            val page = params.key ?: 1
            var imageList: List<Content> = emptyList()
            var videoList: List<Content> = emptyList()

            if (!isEndImage) {
                searchService.searchImage(keyword = keyword, page = page, size = PAGE_SIZE)
                    .let { response ->
                        isEndImage = response.meta.isEnd
                        imageList = response.documents.map { it.toContentList() }
                    }
            }
            if (!isEndVideo) {
                searchService.searchVideo(keyword = keyword, page = page, size = PAGE_SIZE)
                    .let { response ->
                        isEndVideo = response.meta.isEnd
                        videoList = response.documents.map { it.toContentList() }
                    }
            }

            tempContents.addAll(imageList + videoList)
            if (isEndImage && imageList.isNotEmpty()) imageList = emptyList()
            if (isEndVideo && videoList.isNotEmpty()) videoList = emptyList()

            tempContents.sortByDescending { it.dateTime }

            val currentContents =
                if (tempContents.size >= PAGE_SIZE) {
                    tempContents.subList(0, PAGE_SIZE - 1)
                } else {
                    isEndTemp = true
                    tempContents
                }

            tempContents =
                if (currentContents.size == PAGE_SIZE)
                    tempContents.subList(PAGE_SIZE, tempContents.size - 1)
                else mutableListOf()

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

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    companion object {
        private const val PAGE_SIZE = 30
    }
}
