package com.tenpo.lromero.transacsback.swagger;

public class SwaggerExamples {

    public static final String ERROR_400_EXAMPLE = """
                {
                  "timestamp": "2025-04-28T21:09:46.4736508",
                  "status": 400,
                  "error": "Validation Error",
                  "message": {
                    "amount": "Amount must be zero or positive"
                  }
                }
            """;

    public static final String ERROR_500_EXAMPLE = """
                {
                  "timestamp": "2025-04-28T21:09:46.4736508",
                  "status": 500,
                  "error": "Internal Server Error",
                  "message": "Unexpected server error occurred"
                }
            """;

    private SwaggerExamples() {
        // Utility class, no instances
    }
}