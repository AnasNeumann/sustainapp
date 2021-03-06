#
# This is an example VCL file for Varnish.
#
# It does not do anything by default, delegating control to the
# builtin VCL. The builtin VCL is called when there is no explicit
# return statement.
#
# See the VCL chapters in the Users Guide at https://www.varnish-cache.org/docs/
# and http://varnish-cache.org/trac/wiki/VCLExamples for more examples.

# Marker to tell the VCL compiler that this VCL has been adapted to the
# new 4.0 format.
vcl 4.0;

# Default backend definition. Set this to point to your content server.
backend default {
    .host = "127.0.0.1";
    .port = "1234";
}

backend sustainappws {
    .host = "127.0.0.1";
    .port = "8085";
}

backend jenkins {
    .host = "127.0.0.1";
    .port = "8080";
}

sub vcl_recv {
    if (req.url ~ "jenkins") {
        set req.backend_hint = jenkins;
    } else if (req.url ~ "sustainappws") {
       set req.url = regsub(req.url, "sustainappws", "");
       set req.backend_hint = sustainappws;
    }
}

sub vcl_backend_response {
    # Happens after we have read the response headers from the backend.
    #
    # Here you clean the response headers, removing silly Set-Cookie headers
    # and other mistakes your backend does.
}

sub vcl_deliver {
    # Happens when we have all the pieces we need, and are about to send the
    # response to the client.
    #
    # You can do accounting or modifying the final object here.
}

