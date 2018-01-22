package com.schibsted.server.utils

import spock.lang.Specification 
import com.schibsted.server.utils.UrlParserUtils
import java.net.URI

class UrlParserUtilsSpec extends Specification {
	
	def "empty body should return no params"() {
		given:
			def body = "";
		when:
			def params = UrlParserUtils.getFormParametersFromBody(body);
		then:
			params.isEmpty()
	}
	
	def "parameter without value  should return empty string"() {
		given:
			def body = "username=aaa&url&password=bbb";
		when:
			def params = UrlParserUtils.getFormParametersFromBody(body);
		then:
			params.get("url") == ""
	}
	
	def "all parameters should be parsed and decoded if well formed"() {
		given:
			def body = "username=aaa&url=%2Fprivate%2Fpage1&password=bbb";
		when:
			def params = UrlParserUtils.getFormParametersFromBody(body);
		then:
			params.size() == 3
			params.get("url") == "/private/page1"
	}
	
	def "Bad formed parameters are ignored"() {
		given:
		   def body = "username=aaa=bbb&password=ccc"
		when:
			def params = UrlParserUtils.getFormParametersFromBody(body)
		then:
			params.size() == 1
			params.get("password") == "ccc"
	}
	
	def "Well formed URL returns expected value" () {
		given:
			def url = "/api/users/user1"
		when:
			def parameters = [:]
			UrlParserUtils.parseAPIUrl(url, parameters)
		then:
			parameters["PATHLEVEL3"] == "user1"
	}
	
	def "Non compliant URL returns expected value" () {
		given:
			def url = "/api+AAA/users/user1/FAADD+20"
		when:
			def parameters = [:]
			UrlParserUtils.parseAPIUrl(url, parameters)
		then:
			parameters["PATHLEVEL4"] == "FAADD+20"
	}
	
	def "Empty URL returns null value" () {
		given:
			def url = ""
		when:
			def parameters = [:]
			UrlParserUtils.parseAPIUrl(url, parameters)
		then:
			parameters["PATHLEVEL1"] == null
	}
	
	def "Parses all data from well given URI"() {
		given:
			URI uri = new URI("http://localhost:8080/api/users/user1?prueba=ok&test=fail")
		when:
		    def parameters = UrlParserUtils.parseUrlParameters(uri)
		then:
			parameters["PATHLEVEL1"] == "api"
			parameters["PATHLEVEL3"] == "user1"
			parameters["prueba"] == "ok"
			parameters["test"] == "fail"
	}
	
}
