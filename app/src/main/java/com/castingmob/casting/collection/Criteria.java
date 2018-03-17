package com.castingmob.casting.collection;

import com.castingmob.Feed.model.Filter;
import com.castingmob.casting.model.CastingItem;

import java.util.List;

/**
 * Created by nishantdande on 21/09/16.
 */

public interface Criteria {

    List<CastingItem> meetCriteria(List<CastingItem> castingItems);
    List<CastingItem> meetCriteriaByLocation(List<CastingItem> castingItems,
                                             Filter filter);
}
