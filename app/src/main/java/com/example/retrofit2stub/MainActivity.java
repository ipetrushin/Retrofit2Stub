package com.example.retrofit2stub;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    String API_URL = "https://pixabay.com/";
    String q = "bad dog";
    // TODO: зарегистрироваться и вставить свой ключ
    String key = "your key here";
    String image_type = "photo";

    // TODO: реализовать скачивание и отображение картинок, найденных по запросу
    // TODO: добавить возможность выбора типа картинки (image_type)

    interface PixabayAPI {
        @GET("/api") // метод запроса (POST/GET) и путь к API
        // пример содержимого веб-формы q=dogs+and+people&key=MYKEY&image_type=photo
        Call<Response> search(@Query("q") String q, @Query("key") String key, @Query("image_type") String image_type);
        // Тип ответа, действие, содержание запроса

        @GET()
        Call<ResponseBody> getImage (@Url String pictureURL);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startSearch("yellow");

    }

    public void startSearch(String text) {
        // вызывается, когда пользователь вводит текст и нажимает кнопку поиска

        // создаём экземпляр службы для обращения к API
        // можно использовать экземпляр для нескольких API сразу
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL) // адрес API сервера
           //     .addConverterFactory(GsonConverterFactory.create())
                .build();

        // создаём обработчик, определённый интерфейсом PixabayAPI выше
        PixabayAPI api = retrofit.create(PixabayAPI.class);

        Call<ResponseBody> getImage = api.getImage("https://cdn.pixabay.com/photo/2020/04/03/04/47/animal-4997424_150.jpg");
        Callback<ResponseBody> imagecallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                Log.d("mytag", "size: " + bmp.getByteCount());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("mytag", "fail:" + t.getLocalizedMessage());
            }
        };
        getImage.enqueue(imagecallback);
        // указываем, какую функцию API будем использовать
        /*
        Call<Response> call = api.search(text, key, image_type);

        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                // класс Response содердит поля, в которые будут записаны
                // результаты поиска по картинкам
                Response r = response.body(); // получили ответ в виде объекта
                // TODO: отобразить, сколько картинок найдено
                displayResults(r.hits);
                Log.d("mytag", "hits:" + r.hits.length); // сколько картинок нашлось
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                // обрабатываем ошибку, если она возникла
                // TODO: при возникновении ошибки вывести Toast
                Log.d("mytag", "Error: " + t.getLocalizedMessage());
            }
        };
        call.enqueue(callback); // ставим запрос в очередь

         */

    }

    public void displayResults(Hit[] hits) {
        // вызывается, когда появятся результаты поиска

    }

    public void onSearchClick(View v) {


    }
}
