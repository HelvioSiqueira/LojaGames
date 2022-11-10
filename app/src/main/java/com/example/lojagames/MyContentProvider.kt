package com.example.lojagames

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class MyContentProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = -1

    override fun getType(uri: Uri) = PROVIDER_TYPE

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun onCreate(): Boolean {
        TODO("Implement this to initialize your content provider on startup.")
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        TODO("Implement this to handle query requests from clients.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = -1

    companion object{
        private const val COLUMN_GAMES = "games"
        private const val AUTHORITY = "${BuildConfig.APPLICATION_ID}.provider"
        private const val PROVIDER_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY/$COLUMN_GAMES"
        private const val CONTENT_URI = "content://$AUTHORITY"
    }
}