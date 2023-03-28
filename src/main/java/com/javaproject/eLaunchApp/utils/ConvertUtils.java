package com.javaproject.eLaunchApp.utils;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.models.Deliverer;

public class ConvertUtils {

    public  static DelivererDTO convert (Deliverer deliverer) {
        DelivererDTO delivererDTO = new DelivererDTO();
        delivererDTO.setUuid(deliverer.getUuid());
        ///....

        return delivererDTO;
    }

}
