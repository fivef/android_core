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
package sp.GyroTeleop;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import tcm200_msgs.Velocity;

import android.util.Log;

/**
 * A simple {@link Publisher} {@link NodeMain}.
 * 
 * @author damonkohler@google.com (Damon Kohler)
 */
public class MotorSpeedNode extends AbstractNodeMain {

	private int leftMotorSpeed = 0;
	private int rightMotorSpeed = 0;

	@Override
	public GraphName getDefaultNodeName() {
		// this sets the node name
		return GraphName.of("android_gyro_teleop/motor_speeds");
	}

	@Override
	public void onStart(final ConnectedNode connectedNode) {
		Log.d("MotorSpeedNode", "onstart");
		final Publisher<Velocity> publisher = connectedNode
				.newPublisher("motor_speeds", Velocity._TYPE);
		// This CancellableLoop will be canceled automatically when the node
		// shuts
		// down.
		connectedNode.executeCancellableLoop(new CancellableLoop() {
			private int sequenceNumber;

			@Override
			protected void setup() {
				sequenceNumber = 0;
				Log.d("MotorSpeedNode", "setup loop");
			}

			@Override
			protected void loop() throws InterruptedException {

				Velocity vel = publisher.newMessage();
				vel.setMotor_1_velocity(leftMotorSpeed);
				vel.setMotor_2_velocity(rightMotorSpeed);

				Log.d("MotorSpeedNode", "leftMotorSpeed " + leftMotorSpeed
						+ " rightMotorSpeed " + rightMotorSpeed + "");

				publisher.publish(str);

				sequenceNumber++;
				Thread.sleep(100);
			}
		});
	}

	public void setLeftMotorSpeed(int speed) {

		leftMotorSpeed = speed;

	}

	public void setRightMotorSpeed(int speed) {

		rightMotorSpeed = speed;
	}
}
