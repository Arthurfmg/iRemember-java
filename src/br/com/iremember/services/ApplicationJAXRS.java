package br.com.iremember.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.jettison.JettisonFeature;

public class ApplicationJAXRS extends Application{
	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<>();
		properties.put("jersey.config.server.provider.packages", "br.com.iremember.services");
		properties.put("jersey.config.server.wadl.generatorConfig", WADLConfig.class.getCanonicalName());
		return properties;
	}
	
	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();
		singletons.add(new JettisonFeature());
		
		return singletons;
	}
}