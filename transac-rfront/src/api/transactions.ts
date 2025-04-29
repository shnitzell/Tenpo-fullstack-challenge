import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080/api/v1";

export interface Transaction {
  id?: number;
  amount: number;
  commerce: string;
  tenpistaName: string;
  transactionDate: string;
}

export const getTransactions = async (): Promise<Transaction[]> => {
  const { data } = await axios.get(`${API_URL}/transactions`);
  return data;
};

export const createTransaction = async (
  transaction: Transaction
): Promise<Transaction> => {
  const { data } = await axios.post(`${API_URL}/transactions`, transaction);
  return data;
};

export const updateTransaction = async (
  transaction: Transaction
): Promise<Transaction> => {
  const { data } = await axios.put(
    `${API_URL}/transactions/${transaction.id}`,
    transaction
  );
  return data;
};

export const deleteTransaction = async (id: number): Promise<void> => {
  await axios.delete(`${API_URL}/transactions/${id}`);
};
