package com.example.lojagames

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.BaseColumns
import com.example.lojagames.http.model.Game
import com.example.lojagames.list.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

class MyContentProvider : ContentProvider(), KoinComponent, CoroutineScope{

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private val viewModel: ListViewModel by inject()

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = -1

    override fun getType(uri: Uri) = PROVIDER_TYPE

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun onCreate(): Boolean {

        job = Job()

        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        var searchResult: List<Game>?


        val columns = arrayOf(BaseColumns._ID, "TITLE", "PUBLISHER", "IMAGE", "DISCOUNT", "PRICE", "DESCRIPTION", "RATING", "STARTS", "REVIEWS")

        val matrixCursor = MatrixCursor(columns)

        launch {
            searchResult = viewModel.getSearch(selection)

            matrixCursor.addRow(searchResult)
        }

        return matrixCursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = -1

    companion object{
        private const val COLUMN_GAMES = "games"
        private const val AUTHORITY = "${BuildConfig.APPLICATION_ID}.provider"
        private const val PROVIDER_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY/$COLUMN_GAMES"
        const val CONTENT_URI = "content://$AUTHORITY/$COLUMN_GAMES/"
    }
}