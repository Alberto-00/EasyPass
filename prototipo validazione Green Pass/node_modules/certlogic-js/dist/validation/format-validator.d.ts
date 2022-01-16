import { ValidationError } from "./typings";
/**
 * Validates the given JSON as a CertLogic expression in terms of its format.
 * This is similar to validating the JSON against a JSON Schema,
 * but this validation gives better, more accurate feedback.
 */
export declare const validateFormat: (expr: any) => ValidationError[];
//# sourceMappingURL=format-validator.d.ts.map