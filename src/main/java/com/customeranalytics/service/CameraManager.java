package com.customeranalytics.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.customeranalytics.domain.Camera;
import com.customeranalytics.domain.enumeration.CameraType;
import com.customeranalytics.repository.CameraRepository;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamCompositeDriver;
import com.github.sarxos.webcam.ds.buildin.WebcamDefaultDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import com.innovatrics.iface.FaceHandler;
import com.innovatrics.iface.IFace;
import com.innovatrics.iface.enums.AgeGenderSpeedAccuracyMode;
import com.innovatrics.iface.enums.FacedetSpeedAccuracyMode;
import com.innovatrics.iface.enums.Parameter;

@Service
public class CameraManager {
	

	List<CameraManagerItem> cameraItemlist = new ArrayList<CameraManagerItem>();
	

	private final  CameraRepository cameraRepository;
	private final  NotifyService notifyService;
	
	IFace iface= null;
	FaceHandler faceHandler = null;
	
	/**
	 * Customized webcam driver.
	 */
	public static class MyCompositeDriver extends WebcamCompositeDriver {

		public MyCompositeDriver() {
			add(new WebcamDefaultDriver());
			add(new IpCamDriver());
		}
	}

	// register custom composite driver
	static {
		Webcam.setDriver(new MyCompositeDriver());
	}
	
	 @PostConstruct
	public void init() throws IOException{
		//start();
	}
	
	public CameraManager(CameraRepository cameraRepository,NotifyService notifyService) throws IOException {
		super();
		this.cameraRepository = cameraRepository;
		this.notifyService = notifyService;
	}

	private void registerAll() throws IOException{
		List<Camera> list = cameraRepository.findAll();
		iface = IFace.getInstance();
		ClassPathResource cpr = new ClassPathResource("iengine.lic");
		byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
		iface.initWithLicence(bdata);
		
		faceHandler = new FaceHandler();
		faceHandler.setParam(Parameter.FACEDET_SPEED_ACCURACY_MODE, FacedetSpeedAccuracyMode.FAST.toString());
		faceHandler.setParam(Parameter.AGEGENDER_SPEED_ACCURACY_MODE, AgeGenderSpeedAccuracyMode.FAST.toString());
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Camera camera = (Camera) iterator.next();
				if(!camera.getType().equals(CameraType.WEBCAM))
					IpCamDeviceRegistry.register(camera.getName(), camera.getIpaddress(), IpCamMode.PUSH);	
				CameraManagerItem item = new CameraManagerItem(notifyService);
				item.setup(camera, true, faceHandler);
				cameraItemlist.add(item);
			
		}

//		//add built in camera
//		Camera camera = new Camera();
//		camera.setName("built in camera");
//		cameraItemlist.add(new CameraManagerItem(camera,true,faceHandler));
		
		
		
	}
	
	private void unregisterAll(){
		IpCamDeviceRegistry.unregisterAll();
		for (Iterator iterator = cameraItemlist.iterator(); iterator.hasNext();) {
			CameraManagerItem cameraManagerItem = (CameraManagerItem)iterator.next();
			cameraManagerItem.stop();
		}
		cameraItemlist.clear();
	}
	
	
	
	
	public void start() throws IOException{
		registerAll();
	}
	public void stop() throws MalformedURLException{
		unregisterAll();
	}
	public void restart() throws IOException{
		stop();
		start();
	}
	
	@Scheduled(fixedRate = 30000)
	public void clearCache() throws IOException{
		for (Iterator iterator = cameraItemlist.iterator(); iterator.hasNext();) {
			CameraManagerItem cameraManagerItem = (CameraManagerItem) iterator.next();
			cameraManagerItem.clearCache();
		}
	}
	
	
	@Scheduled(fixedRate = 2000)
	public void capture() throws IOException{
		for (Iterator iterator = cameraItemlist.iterator(); iterator.hasNext();) {
			CameraManagerItem cameraManagerItem = (CameraManagerItem) iterator.next();
			cameraManagerItem.capture();
		}
	}
	
	
	
	
	
	
	
	
	
}
