package com.bluemoonllc.ctms.api.service.bi;

import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Tariff;

public interface TariffBI {

    CtmsResponse addTariff(Tariff tariff);

    CtmsResponse addNewStation(String stationCode, Tariff tariff);
}
