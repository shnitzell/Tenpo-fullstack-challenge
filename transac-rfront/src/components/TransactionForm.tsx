import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createTransaction, Transaction } from "../api/transactions.ts";
import { useState } from "react";

export const TransactionForm = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: createTransaction,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["transactions"] });
    },
  });

  const [form, setForm] = useState<Transaction>({
    amount: 0,
    commerce: "",
    tenpistaName: "",
    transactionDate: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    mutation.mutate(form);
  };

  return (
    <div className="max-w-md mx-auto mt-10">
      <h1 className="text-2xl font-bold mb-6">New Transaction</h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          type="number"
          name="amount"
          placeholder="Amount"
          value={form.amount}
          onChange={handleChange}
          className="w-full p-2 border rounded"
          required
        />

        <input
          type="text"
          name="commerce"
          placeholder="Commerce"
          value={form.commerce}
          onChange={handleChange}
          className="w-full p-2 border rounded"
          required
        />

        <input
          type="text"
          name="tenpistaName"
          placeholder="Tenpista Name"
          value={form.tenpistaName}
          onChange={handleChange}
          className="w-full p-2 border rounded"
          required
        />

        <input
          type="datetime-local"
          name="transactionDate"
          value={form.transactionDate}
          onChange={handleChange}
          className="w-full p-2 border rounded"
          required
        />

        <button
          type="submit"
          className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600"
        >
          Save
        </button>
      </form>
    </div>
  );
};
