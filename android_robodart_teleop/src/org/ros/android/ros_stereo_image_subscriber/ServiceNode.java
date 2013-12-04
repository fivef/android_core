package org.ros.android.ros_stereo_image_subscriber;

import org.ros.exception.ServiceNotFoundException;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.service.ServiceClient;
import org.ros.node.topic.Publisher;

import std_srvs.Empty;

public class ServiceNode implements NodeMain {

	@Override
	public void onError(Node arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onShutdown(Node arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onShutdownComplete(Node arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ConnectedNode node) {
		// TODO Auto-generated method stub
		Publisher<Empty.Request> publisher = node.newPublisher("/robodart_control/throw_dart", "Empty");
		
		try {
			ServiceClient<Empty.Request, Empty.Request> service = node.newServiceClient("/robodart_control/throw_dart", "Empty");
		} catch (ServiceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public GraphName getDefaultNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

}
