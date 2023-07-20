package ru.develgame.gpsviewer.renders;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import ru.develgame.gpsdomain.GPSReceivedData;

import java.text.SimpleDateFormat;

public class PointsRender implements ListitemRenderer<GPSReceivedData> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");

    @Override
    public void render(Listitem listitem, GPSReceivedData gpsReceivedData, int i) throws Exception {
        listitem.appendChild(new Listcell(dateFormat.format(gpsReceivedData.getTime())));
        listitem.appendChild(new Listcell(Double.toString(gpsReceivedData.getLatitude())));
        listitem.appendChild(new Listcell(Double.toString(gpsReceivedData.getLongitude())));
    }
}
