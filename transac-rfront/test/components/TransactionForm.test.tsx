import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import { TransactionForm } from "@/components/TransactionForm";

describe("TransactionForm Component", () => {
  test("renders without crashing", () => {
    render(
      <TransactionForm
        transaction={{
          amount: 0,
          commerce: "",
          tenpistaName: "",
          transactionDate: new Date().toISOString(),
        }}
        onSubmit={() => {}}
        onCancel={() => {}}
      />
    );
    const formElement = screen.getByTestId("transaction-form");
    expect(formElement).toBeInTheDocument();
  });
});
