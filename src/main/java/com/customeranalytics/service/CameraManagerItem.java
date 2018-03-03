package com.customeranalytics.service;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.customeranalytics.domain.Camera;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.innovatrics.iface.Face;
import com.innovatrics.iface.FaceHandler;
import com.innovatrics.iface.enums.FaceAttributeId;

@Component	
public class CameraManagerItem {

    private final Logger log = LoggerFactory.getLogger(CameraManagerItem.class);
	
	public int minEyeDistance = 30;
    public int maxEyeDistance = 200;
	
	Camera camera;
	Boolean runnable;
	Webcam webcam;
	FaceHandler faceHandler;
	
	
	private final NotifyService notifyService;
	
	Map<Long,byte[]> faceBuffer = new ConcurrentHashMap<Long,byte[]>();
	
	public CameraManagerItem(NotifyService notifyService) throws MalformedURLException {
		super();
		this.notifyService = notifyService;
		
	}

	public void setup(Camera camera,Boolean runnable,FaceHandler faceHandler) throws MalformedURLException{
		setCamera(camera);
		setRunnable(runnable);
		setFaceHandler(faceHandler);
		
		
		for (Iterator iterator = Webcam.getWebcams().iterator(); iterator.hasNext();) {
			Webcam temp = (Webcam) iterator.next();
			if(temp.getName().equals(camera.getName())){
				webcam = temp;
				
			}	
		}
		if(webcam==null){
			webcam = Webcam.getWebcams().get(0);
		}	
		Dimension captureResolution = WebcamResolution.VGA.getSize(); // 640 x 480
		webcam.setViewSize(captureResolution);
		webcam.open();
	}
	
	//@Scheduled(fixedRate = 3000)
	@Async
	public void clearCache() throws IOException{
		for (Iterator iterator = faceBuffer.keySet().iterator(); iterator.hasNext();) {
			Long duration = (Long) iterator.next();
			if((System.currentTimeMillis()-duration)>5000)
				iterator.remove();
		}
		log.info("buffer temizlendi");
	}
	
	//@Scheduled(fixedRate = 2000)
	@Async
	public void capture() throws IOException{
		if(!runnable)
			return;
		
		if(webcam==null)
			return;
		
		BufferedImage image  =webcam.getImage();
		
		
		Face[] faces = faceHandler.detectFaces(convertToByteArray(image), minEyeDistance, maxEyeDistance, 3);
		if(faces.length==0){
			log.info("no face detected");
			return;
		}
		
		for (int i = 0; i < faces.length; i++) {
			Face face = faces[i];
			if(checkFaceBuffer(face.createTemplate())){
				log.info("bufferFace içinde bulundu");
				continue;
			}
			Float age = face.getAttribute(FaceAttributeId.AGE);
	        Float gender = face.getAttribute(FaceAttributeId.GENDER);
	        faceBuffer.put(System.currentTimeMillis(), face.createTemplate());
	        notifyService.sendNotify(age, gender);
		}
	}
	
	
	private Boolean checkFaceBuffer(byte[] currentFace){
		for (Iterator iterator = faceBuffer.keySet().iterator(); iterator.hasNext();) {
			Long duration = (Long)iterator.next();
			if((System.currentTimeMillis()-duration)<5000)
				continue;
			
			byte[] type = faceBuffer.get(duration) ;
			float score =faceHandler.matchTemplate(type, currentFace);
			if(score>600){
				log.info("aynı kişi tespit edildi.score = "+score);
				return true;
			}
		}
		return false;
	}
	
	private byte[] convertToByteArray(BufferedImage originalImage) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( originalImage, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void start(){
		runnable = true;
	}
	public void stop(){
		runnable = false;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Boolean getRunnable() {
		return runnable;
	}

	public void setRunnable(Boolean runnable) {
		this.runnable = runnable;
	}

	public FaceHandler getFaceHandler() {
		return faceHandler;
	}

	public void setFaceHandler(FaceHandler faceHandler) {
		this.faceHandler = faceHandler;
	}

	public Map<Long, byte[]> getFaceBuffer() {
		return faceBuffer;
	}

	public void setFaceBuffer(Map<Long, byte[]> faceBuffer) {
		this.faceBuffer = faceBuffer;
	}
	
}
