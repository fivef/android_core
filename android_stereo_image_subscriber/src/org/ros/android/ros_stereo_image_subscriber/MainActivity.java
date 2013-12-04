/*
 * Copyright (C) 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ros.android.ros_stereo_image_subscriber;

import org.ros.address.InetAddressFactory;
import org.ros.android.BitmapFromCompressedImage;
import org.ros.android.RosActivity;
import org.ros.android.android_tutorial_image_transport.R;
import org.ros.android.view.RosImageView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * @author ethan.rublee@gmail.com (Ethan Rublee)
 * @author damonkohler@google.com (Damon Kohler)
 */
public class MainActivity extends RosActivity {

  private RosImageView<sensor_msgs.CompressedImage> image;
  private RosImageView<sensor_msgs.CompressedImage> image2;

  public MainActivity() {
    super("ImageTransportTutorial", "ImageTransportTutorial");
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    /*Danger!!! this is a very very bad hack to avoid android.os.NetworkOnMainThreadException
    In android_core_gingerbread_m1/src/org/ros/android/NodeMainExecutorService.java
    startMaster() should be done in a async task to probably avoid the exception
    */
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy); 
    
    //Fullscreen
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.main);
    //Wakelock
    getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);

    image = (RosImageView<sensor_msgs.CompressedImage>) findViewById(R.id.image);
    image.setTopicName("/camera/image/compressed");
    image.setMessageType(sensor_msgs.CompressedImage._TYPE);
    image.setMessageToBitmapCallable(new BitmapFromCompressedImage());

    image2 = (RosImageView<sensor_msgs.CompressedImage>) findViewById(R.id.image2);
    image2.setTopicName("/camera2/image/compressed");
    image2.setMessageType(sensor_msgs.CompressedImage._TYPE);
    image2.setMessageToBitmapCallable(new BitmapFromCompressedImage());
    
  }

  @Override
  protected void init(NodeMainExecutor nodeMainExecutor) {
    NodeConfiguration nodeConfiguration =
        NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress(),
            getMasterUri());
    nodeMainExecutor.execute(image, nodeConfiguration.setNodeName("android/video_view"));
    nodeMainExecutor.execute(image2, nodeConfiguration.setNodeName("android/video_view2"));

  }
  
  @Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		
	    //Full brightness
	    Window window = getWindow();
	    WindowManager.LayoutParams lp = window.getAttributes();
	    lp.screenBrightness = 1.0f;
	    window.setAttributes(lp);
	}
  
  /*
  @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity, menu);
	    //menu.getItem(0).setTitle(getMasterUri().toString());
	    return;
	}
	*/

}
