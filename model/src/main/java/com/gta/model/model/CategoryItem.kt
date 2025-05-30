package com.gta.model.model

import android.net.Uri



open class GridItem(
    override val layoutId: Int,
    val title: String?="hello",
    val subtitle: String?="world",
    val imageUri: Uri?=null,
    override val contentDescription: String,
    override val id: String,
    override val payload: Any? = null
) : ListItem

interface ListItem  {
    val id: String

    val layoutId: Int

    val contentDescription: String?

    @Deprecated("暂不支持")
    val payload: Any?

}