package com.gta.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_classify")
data class ProjectClassify(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    //@ColumnInfo(name = "children") val children: List<Any> = arrayListOf(),
    @ColumnInfo(name = "course_id") val courseId: Int,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "order_classify") val order: Int,
    @ColumnInfo(name = "parent_chapter_id") val parentChapterId: Int,
    @ColumnInfo(name = "user_control_set_top") val userControlSetTop: Boolean,
    @ColumnInfo(name = "visible") val visible: Int
)
// 历史记录 localType
const val HISTORY = 10

// 首页置顶 localType
const val HOME_TOP = 20

// 首页 localType
const val HOME = 30

// 项目 localType
const val PROJECT = 40

// 公众号 localType
const val OFFICIAL = 50