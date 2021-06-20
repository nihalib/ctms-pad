package com.bluemoonllc.ctms.api.service.bi;

import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Station;
import com.bluemoonllc.ctms.model.Tariff;

public interface TariffBI {

    CtmsResponse getTariff(String fetchType, Integer page, Integer pageSize);

    CtmsResponse addTariff(Tariff tariff, Boolean testMode);

    CtmsResponse addNewStation(Station station, Boolean testMode);

    CtmsResponse getStation(String fetchType, Integer page, Integer pageSize);

    CtmsResponse getStationByLocation(String fetchType, String location, Integer page, Integer pageSize);
}
