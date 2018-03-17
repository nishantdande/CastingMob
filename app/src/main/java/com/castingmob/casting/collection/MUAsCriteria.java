package com.castingmob.casting.collection;

import com.castingmob.Feed.model.Filter;
import com.castingmob.casting.model.CastingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishantdande on 21/09/16.
 */

public class MUAsCriteria implements Criteria {

    @Override
    public List<CastingItem> meetCriteria(List<CastingItem> castingItems) {
        List<CastingItem> sortedCastingItems = new ArrayList<CastingItem>();

        for (CastingItem castingItem: castingItems) {
            if(castingItem.getUserType().equalsIgnoreCase("makeupartist")){
                sortedCastingItems.add(castingItem);
            }
        }
        return sortedCastingItems;
    }

    @Override
    public List<CastingItem> meetCriteriaByLocation(List<CastingItem> castingItems,
                                                    Filter filter) {
        List<CastingItem> sortedCastingItems = new ArrayList<CastingItem>();

        for (CastingItem castingItem: castingItems) {
            if(castingItem.getUserType().equalsIgnoreCase("makeupartist") &&
                    (castingItem.getLocation().contains(filter.getCityCasting()) ||
                    castingItem.getLocation().contains(filter.getStateCasting()) ||
                    castingItem.getLocation().contains(filter.getCountryCasting()))){
                sortedCastingItems.add(castingItem);
            }
        }
        return sortedCastingItems;
    }
}
