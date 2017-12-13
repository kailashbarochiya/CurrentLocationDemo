# CurrentLocationDemo

<p>Add it in your root build.gradle at the end of repositories:<br />allprojects <br />{<br /> repositories<br /> {<br /> maven { url 'https://jitpack.io' }<br /> }<br /> }<br /> <br /> <br /> Add the dependency<br /> dependencies<br /> {<br /> compile 'com.github.kailashbarochiya:CurrentLocationDemo:0.1.1'<br /> }</p><br/><br/>

<pre>Location location=new Location(LocationActivity.this);<br />location.getLocation();<br /><br />if(getmCurrentLocation()!=null)<br />{<br />    Log.d("CurrentLatlong==","Lat:"+getmCurrentLocation().getLatitude()+" Lng:"+getmCurrentLocation().getLongitude());<br /><br />}</pre>
  
