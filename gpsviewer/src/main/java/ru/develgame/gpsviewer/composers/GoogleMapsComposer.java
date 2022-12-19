package ru.develgame.gpsviewer.composers;

import org.springframework.web.client.RestTemplate;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.LatLng;
import org.zkoss.gmaps.MapModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import ru.develgame.gpsdomain.GPSReceivedData;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GoogleMapsComposer extends SelectorComposer<Component>{
	@Wire
	private Gmaps gmaps;

	@WireVariable
	private RestTemplate restTemplate;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		GPSReceivedData[] gpsReceivedData = restTemplate.getForObject("http://localhost:8111/data", GPSReceivedData[].class);
		if (gpsReceivedData != null && gpsReceivedData.length > 0) {
			gmaps.setCenter(new LatLng(gpsReceivedData[0].getLatitude(), gpsReceivedData[0].getLongitude()));

			for (GPSReceivedData elem : gpsReceivedData) {
				Gmarker gmarker = new Gmarker();
				gmarker.setLat(elem.getLatitude());
				gmarker.setLng(elem.getLongitude());
				gmaps.appendChild(gmarker);
			}
		}
	}
}
