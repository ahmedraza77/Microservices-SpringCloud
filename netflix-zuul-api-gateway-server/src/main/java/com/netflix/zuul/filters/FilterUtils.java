package com.netflix.zuul.filters;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;


@Component
public class FilterUtils {
    public static final String CORRELATION_ID = "x-correlation-id";
    public static final String SLEUTH_TRACE_ID = "sleuth-trace-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";
    public static final int FILTER_ORDER =  1;
    public static final boolean SHOULD_FILTER=true;
    
    private final int MAX_ID_SIZE = 10;
    
    public boolean isCorrelationIdPresent(){
        if (getCorrelationId() !=null){
            return true;
        }
        return false;
      }
    
    public String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }
    
    public String getCorrelationId(){
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.getRequest().getHeader(CORRELATION_ID) !=null) {
            return ctx.getRequest().getHeader(CORRELATION_ID);
        }
        else{
            return  ctx.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    public void setCorrelationId(String correlationId){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }
    
    
    //******** for correlationId Filter
    public String verifyOrCreateId(String correlationId)
    {
       if(correlationId == null)
       {
          correlationId = generateId();
       }
       // fills logs with long correlation id provided by client
       else if (correlationId.length() > MAX_ID_SIZE)
       {
          correlationId = correlationId.substring(0, MAX_ID_SIZE);
       }
  
       return correlationId;
  
    }
    
    public String generateId(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        
        return random.ints(leftLimit, rightLimit + 1)
        	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        	      .limit(targetStringLength)
        	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        	      .toString();
    }

}
