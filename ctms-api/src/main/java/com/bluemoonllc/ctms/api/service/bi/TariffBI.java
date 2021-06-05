package com.bluemoonllc.ctms.api.service.bi;

import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Tariff;

public interface TariffBI {

    CtmsResponse getTariff(Integer page, Integer pageSize);

    CtmsResponse addTariff(Tariff tariff);

    CtmsResponse addNewStation(String location, Tariff tariff);

    CtmsResponse getStation(String location);
}
