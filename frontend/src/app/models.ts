export interface Unit {
  id: number;
  label: string;
  symbol: string;
}

export interface CalculationRequest {
  fromValue: number;
  toValue?: number;
  fromUnit: string;
  toUnit?: string;
  actionType: string;
  operator?: string;
}

/**
 * Matches QuantityMeasurementEntity from the user-service backend.
 * Fields: id, userId, operation, operand1, operand2, result, errorMessage
 */
export interface HistoryRecord {
  id: number;
  userId: number;
  operation: string;
  operand1: string;
  operand2: string;
  result: string;
  errorMessage: string;
}
