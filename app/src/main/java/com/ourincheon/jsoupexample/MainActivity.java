package com.ourincheon.jsoupexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    String test;
    List<String> list = new ArrayList<String>();
/*1. File > Project Structure 선택
2. 상단의 + 선택
3. Import .JAR or .AAR Package 선택
4. jar 선택 후 Subproject name에 해당 항목의 이름을 정한 후 Finish
5. 1번의 Project Structure화면에서 app > Dependencies 탭 선택
6. 오른쪽의 + 선택 > Module Dependency 선택
7. 4번에서 만든 모듈 선택
8. 끝!
전 그냥 libs 폴더에 jar 넣고 우클릭해서 Add As library 누르면 되던데...*/

/*HashMap은 Map인터페이스의 한 종류로, key와 value 한 쌍을 데이터로 가진다.
* 쉽게 이해하려면 리스트 형태에 값을 키와 벨류로 가지고 있다고 생각하면 된다.
* 리스트와의 큰 차이점은 위에처럼 키값을 가진다는 것이고, 또 순서를 보장하지 않는다는 점이다.
* 입력된 key값을 이용해 value를 구하며 (리스트에서 인덱스를 이용해 value를 구하는 이치)
* key값은 중복되지 않으며 value는 중복 가능하다.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.TextView);
        new Task().execute();


    }

    public class Task extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("http://117.16.225.214:8080/SeatMate.php?classInfo=1")
                        .get();
                Elements parseLine = doc.select("[color]");//color로 parse한 것을 Element에 넣었다
                Map<String, String> mapData = new HashMap<String, String>();

                for(Element e : parseLine){
                    list.add(e.text());
                }
                test = list.get(5);
                Log.e("list",list.get(5));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv.setText(list.get(5));//이렇게 해도 되네? 메인 스레드가 아닌 곳에서는 안된다고 하더니
        }
    }
    /*AsyncTask는 UI 처리 및 Background 작업 등 을 하나의 클래스에서 작업 할 수 있게 지원해 줍니다.
    쉽게말해 메인Thread와 일반Thread를 가지고 Handler를 사용하여 핸들링하지 않아도 AsyncTask 객체하나로 편하게 UI를 수정 할 수 있고,
    Background 작업을 진행 할 수 있습니다. 각각의 주기마다 CallBack 메서드를 사용해서 말이죠.*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
