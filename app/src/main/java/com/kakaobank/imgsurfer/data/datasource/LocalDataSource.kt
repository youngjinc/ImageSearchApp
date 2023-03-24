package com.kakaobank.imgsurfer.data.datasource

import android.content.Context
import androidx.core.content.edit
import com.kakaobank.imgsurfer.data.model.ContentEntity
import com.kakaobank.imgsurfer.domain.model.Content
import com.kakaobank.imgsurfer.util.KakaoLog
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    @ApplicationContext context: Context,
    private val json: Json,
) {
    private val pref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    var contents: List<Content>
        set(value) = pref.edit {
            val contentEntity =
                value.map { ContentEntity(it.imageUrl, it.dateTime.toString(), it.source) }
            putString(KEY_ARCHIVED_CONTENTS, json.encodeToString(contentEntity))
        }
        get() {
            val strContents = pref.getString(KEY_ARCHIVED_CONTENTS, null) ?: return emptyList()
            return try {
                json.decodeFromString<List<ContentEntity>>(strContents).map { it.toContent() }
            } catch (e: SerializationException) {
                e.message?.let { KakaoLog.e(it) }
                emptyList()
            } catch (e: IllegalArgumentException) {
                e.message?.let { KakaoLog.e(it) }
                emptyList()
            }
        }

    fun addArchivedContent(content: Content) {
        val contents = contents.toMutableList()
        contents.add(0, content) // 보관 시간 최신순으로 저장하기 위해 맨 앞(idx = 0)위치에 저장
        this.contents = contents
    }

    fun deleteArchivedContent(content: Content) {
        val contents = contents.toMutableList()
        contents.remove(content)
        this.contents = contents
    }

    /** 검색 결과로 전달된 콘텐츠가 보관함에 저장되어있는지 확인하는 함수 */
    fun isArchivedContent(content: Content?) = contents.contains(content)

    companion object {
        private const val FILE_NAME = "kakaobank_preference"
        private const val KEY_ARCHIVED_CONTENTS = "archivedContents"
    }
}
