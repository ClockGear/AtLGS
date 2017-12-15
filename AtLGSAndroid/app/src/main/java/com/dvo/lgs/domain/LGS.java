package com.dvo.lgs.domain;

/**
 * Created by Dennis van Opstal on 15-12-2017.
 */

public class LGS {
    private long id;
    private String name;
    private String address;

    public LGS(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public LGS(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
