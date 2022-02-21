package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var retService: AlbumService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //把api抓回的資料傳到需要顯示的地方
         retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)//傳入要用到的interface
        //getRequestWithQueryParameters
        //getRequestWithPathParameters()
        uploadAlbum()


    }

    private fun getRequestWithQueryParameters() {
        //利用上面service抓回的資料轉為livedata
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response: Response<Albums> = retService.getSortedAlbums(2)
            emit(response)
        }
        //拿回rest api 的response後 再加上observer個別取出
        //Observer後面的it 表示RetrofitInstance 處理(json->Kotlin)過後的Response<albums>
        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()//listIterator用來整理list資料
            if (albumList!=null){
                while (albumList.hasNext()) {
                    val albumsItem = albumList.next()
                    val result = " "+"Album Title: ${albumsItem.title}"+"\n"+
                            " "+"Album id: ${albumsItem.id}"+"\n"+
                            " "+"Userid: ${albumsItem.userId}"+"\n\n\n"

                    text_view.append(result)


                }
            }
        })
    }

    private fun getRequestWithPathParameters() {

        //path parameter example
        val pathResponse : LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbum(3)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
        })

    }


    private fun uploadAlbum() {
        val album = AlbumsItem(0, "Test Post Title", 3)

        val postResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }
        postResponse.observe(this, Observer {
            val receivedAlbumsItem = it.body()
            val result = " "+"Album Title: ${receivedAlbumsItem?.title}"+"\n"+
                    " "+"Album id: ${receivedAlbumsItem?.id}"+"\n"+
                    " "+"Userid: ${receivedAlbumsItem?.userId}"+"\n\n\n"
            text_view.text = result
        })
    }
}



