package kailash.currentlocation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationlib.CurrentLocation;

import static com.example.locationlib.CurrentLocation.getmCurrentLocation;


public class MainActivity extends AppCompatActivity {


    private TextView tvClcik;
    private TextView tvLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CurrentLocation location=new CurrentLocation(MainActivity.this);
        location.checkPermission();


        tvClcik=(TextView) findViewById(R.id.tvClcik);
        tvLocation=(TextView) findViewById(R.id.tcLoc);



        tvClcik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getmCurrentLocation()!=null)
                {
                    Log.d("CurrentLatlong==","Lat:"+getmCurrentLocation().getLatitude()+" Lng:"+getmCurrentLocation().getLongitude());
                    tvLocation.setText("Your location is : "+getmCurrentLocation().getLatitude()+","+getmCurrentLocation().getLongitude());

                }
                else
                {
                    Toast.makeText(MainActivity.this,"CurrentLocation not found",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }


}
