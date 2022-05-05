package com.example.gql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import reactor.core.publisher.Flux;

import static reactor.core.publisher.Flux.fromArray;

@DgsComponent
public class ShowsDataFetcher {

    private final Show[] shows = new Show[]{new Show("Stranger Things", 2016),
            new Show("Ozark", 2017),
            new Show("The Crown", 2016),
            new Show("Dead to Me", 2019),
            new Show("Orange is the New Black", 2013)};

    @DgsQuery
    public Flux<Show> shows(@InputArgument String titleFilter) {
        if (titleFilter == null) {
            return fromArray(shows);
        }
        return fromArray(shows).filter(s -> s.getTitle().contains(titleFilter));
    }

}