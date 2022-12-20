package ru.develgame.gpsviewer.composers;

import org.springframework.web.client.RestTemplate;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.LatLng;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import ru.develgame.gpsdomain.GPSReceivedData;

import java.text.SimpleDateFormat;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GoogleMapsComposer extends SelectorComposer<Component>{
	@Wire
	private Gmaps gmaps;

	@Wire
	private Combobox dateComboBox;

	@WireVariable
	private RestTemplate restTemplate;

	private ListModelList<String> dateModel;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		String[] allDates = restTemplate.getForObject("http://localhost:8111/getAllDates", String[].class);
		if (allDates != null) {
			for (String elem : allDates) {
				getDateModel().add(elem);
			}
		}
	}

	public ListModelList<String> getDateModel() {
		if (dateModel == null)
			dateModel = new ListModelList<>();

		return dateModel;
	}

	@Listen("onSelect = #dateComboBox")
	public void dateComboBoxOnSelect() {
		GPSReceivedData[] gpsReceivedData = restTemplate.getForObject("http://localhost:8111/data", GPSReceivedData[].class);
		if (gpsReceivedData != null && gpsReceivedData.length > 0) {
			gmaps.setCenter(new LatLng(gpsReceivedData[0].getLatitude(), gpsReceivedData[0].getLongitude()));

			for (GPSReceivedData elem : gpsReceivedData) {
				Gmarker gmarker = new Gmarker();
				gmarker.setLat(elem.getLatitude());
				gmarker.setLng(elem.getLongitude());
				gmarker.setTooltiptext(dateFormat.format(elem.getTime()));
				gmaps.appendChild(gmarker);
			}
		}
	}
}
