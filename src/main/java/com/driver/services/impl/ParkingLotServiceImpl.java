package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address)
    {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setAddress(address);
        parkingLot.setName(name);
        return parkingLotRepository1.save(parkingLot);
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour)
    {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        Spot spot = new Spot();
        spot.setPricePerHour(pricePerHour);

        if(numberOfWheels > 4)
        {
            spot.setSpotType(SpotType.OTHERS);
        }
        else if(numberOfWheels > 2)
        {
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }
        else spot.setSpotType(SpotType.TWO_WHEELER);

        spot.setOccupied(true);
        spot.setParkingLot(parkingLot);

        parkingLot.getSpotList().add(spot);
        parkingLotRepository1.save(parkingLot);
        return spot;
    }

    @Override
    public void deleteSpot(int spotId)
    {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour)
    {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();
        Spot updatedSpot = null;
        for(Spot spot : spotList)
        {
            if(spot.getId() == spotId)
            {
                spot.setPricePerHour(pricePerHour);
                updatedSpot = spot;
                break;
            }
        }
        parkingLot.setSpotList(spotList);
        parkingLotRepository1.save(parkingLot);
        return updatedSpot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId)
    {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
