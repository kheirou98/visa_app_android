package com.myapplication.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_docsechecklist.*
import android.support.v7.widget.DividerItemDecoration
import com.myapplication.R
import com.myapplication.adapter.CheckListAdapter
import com.myapplication.helper.database


class DocsCheckList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_docsechecklist)
        var DB= database(this)
        var DocsName=DB.getAllDocs()
        checklist.layoutManager = LinearLayoutManager(this)
        checklist.adapter= CheckListAdapter(this, DocsName)
        // adding inbuilt divider line
       checklist.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

    }

}

