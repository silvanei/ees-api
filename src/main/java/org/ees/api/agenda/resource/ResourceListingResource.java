package org.ees.api.agenda.resource;

import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.api.model.AbstractSubResourceLocator;
import com.sun.jersey.api.model.AbstractSubResourceMethod;
import com.sun.jersey.server.impl.modelapi.annotation.IntrospectionModeller;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class ResourceListingResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAll(@Context Application application,
                            @Context HttpServletRequest request) {
        String basePath = request.getRequestURL().toString();

        ObjectNode root = JsonNodeFactory.instance.objectNode();
        ArrayNode resources = JsonNodeFactory.instance.arrayNode();

        root.put("resources", resources);

        for (Class<?> aClass : application.getClasses()) {
            if (isAnnotatedResourceClass(aClass)) {
                AbstractResource resource = IntrospectionModeller.createResource(aClass);
                ObjectNode resourceNode = JsonNodeFactory.instance.objectNode();
                String uriPrefix = resource.getPath().getValue();

                if(uriPrefix.equals("/")) {
                    continue;
                }

                for (AbstractSubResourceMethod srm : resource.getSubResourceMethods()) {
                    String uri = uriPrefix + srm.getPath().getValue();
                    addTo(resourceNode, uri, srm, joinUri(basePath, uri));
                }

                for (AbstractResourceMethod srm : resource.getResourceMethods()) {
                    addTo(resourceNode, uriPrefix, srm, joinUri(basePath, uriPrefix));
                }

                if (resource.getSubResourceLocators() != null && !resource.getSubResourceLocators().isEmpty()) {
                    for (AbstractSubResourceLocator subResourceLocator : resource.getSubResourceLocators()) {

                        AbstractResource subresource = IntrospectionModeller.createResource(subResourceLocator.getMethod().getReturnType());
                        String suburiPrefix = subResourceLocator.getPath().getValue();

                        for (AbstractSubResourceMethod srm : subresource.getSubResourceMethods()) {

                            String uri = uriPrefix + suburiPrefix + srm.getPath().getValue();
                            addTo(resourceNode, uri, srm, joinUri(basePath, uri));
                        }

                        for (AbstractResourceMethod srm : subresource.getResourceMethods()) {
                            String uri = uriPrefix + suburiPrefix;
                            addTo(resourceNode, uri, srm, joinUri(basePath, uri));
                        }


                    }
                }

                resources.add(resourceNode);
            }

        }


        return Response.ok().entity(root.toString()).build();
    }

    private void addTo(ObjectNode resourceNode, String uriPrefix, AbstractResourceMethod srm, String path) {
        if (resourceNode.get(uriPrefix) == null) {
            System.out.println(uriPrefix);
            ObjectNode inner = JsonNodeFactory.instance.objectNode();
            inner.put("path", path);
            inner.put("verbs", JsonNodeFactory.instance.arrayNode());
            resourceNode.put(uriPrefix, inner);
        }

        ((ArrayNode) resourceNode.get(uriPrefix).get("verbs")).add(srm.getHttpMethod());
    }

    public static String joinUri(String... parts) {
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (result.length() > 0 && result.charAt(result.length() - 1) == '/') {
                result.setLength(result.length() - 1);
            }
            if (result.length() > 0 && !part.startsWith("/")) {
                result.append('/');
            }
            result.append(part);
        }
        return result.toString();
    }


    private boolean isAnnotatedResourceClass(Class rc) {
        if (rc.isAnnotationPresent(Path.class)) {
            return true;
        }

        for (Class i : rc.getInterfaces()) {
            if (i.isAnnotationPresent(Path.class)) {
                return true;
            }
        }

        return false;
    }

}