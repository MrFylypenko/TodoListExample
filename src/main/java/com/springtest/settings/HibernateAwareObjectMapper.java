package com.springtest.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * Created by vano on 11.12.15.
 */
public class HibernateAwareObjectMapper extends ObjectMapper{

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }

}
