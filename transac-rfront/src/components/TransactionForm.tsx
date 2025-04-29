import { zodResolver } from "@hookform/resolvers/zod";
import * as zod from "zod";

import { useToast } from "../hooks/use-toast";
import { Transaction } from "../api/transactions.ts";

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "../components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../components/ui/form";

import { Input } from "../components/ui/input";
import { Button } from "../components/ui/button";
import { useForm } from "react-hook-form";

interface TransactionFormProps {
  transaction: Transaction | null;
  onSubmit: (transaction: Transaction) => void;
  onCancel: () => void;
}

const transactionSchema = zod.object({
  id: zod.number().optional(),
  amount: zod.coerce
    .number()
    .positive("El monto debe ser positivo")
    .int("El monto debe ser un número entero"),
  commerce: zod
    .string()
    .min(2, "El comercio debe tener al menos 2 caracteres")
    .max(100, "El comercio no puede exceder los 100 caracteres"),
  tenpistaName: zod
    .string()
    .min(2, "El nombre debe tener al menos 2 caracteres")
    .max(100, "El nombre no puede exceder los 100 caracteres"),
  transactionDate: zod.string().refine((date) => {
    const now = new Date();
    const transactionDate = new Date(date);
    return transactionDate <= now;
  }, "La fecha no puede ser futura"),
});

export const TransactionForm = ({
  transaction,
  onSubmit,
  onCancel,
}: TransactionFormProps) => {
  const isNew = transaction === null;
  const { toast } = useToast();

  const form = useForm<zod.infer<typeof transactionSchema>>({
    resolver: zodResolver(transactionSchema),
    defaultValues: {
      id: transaction?.id,
      amount: transaction?.amount ?? 0,
      commerce: transaction?.commerce ?? "",
      tenpistaName: transaction?.tenpistaName ?? "",
      transactionDate: transaction?.transactionDate
        ? new Date(transaction.transactionDate).toISOString().slice(0, 16)
        : new Date().toISOString().slice(0, 16),
    },
  });

  const handleSubmit = (values: zod.infer<typeof transactionSchema>) => {
    try {
      const formattedTransaction: Transaction = {
        id: values.id,
        amount: values.amount,
        commerce: values.commerce,
        tenpistaName: values.tenpistaName,
        transactionDate: values.transactionDate,
      };

      onSubmit(formattedTransaction);
    } catch (error) {
      console.log(error);
      toast({
        variant: "destructive",
        title: "Error",
        description: "Ocurrió un error al guardar la transacción",
      });
    }
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>
          {isNew ? "Nueva Transacción" : "Editar Transacción"}
        </CardTitle>
      </CardHeader>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(handleSubmit)}>
          <CardContent className="space-y-4">
            <FormField
              control={form.control}
              name="amount"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Monto (CLP)</FormLabel>
                  <FormControl>
                    <Input
                      type="number"
                      placeholder="Ingrese el monto"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="commerce"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Comercio</FormLabel>
                  <FormControl>
                    <Input
                      placeholder="Ingrese el nombre del comercio"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="tenpistaName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Nombre del Tenpista</FormLabel>
                  <FormControl>
                    <Input
                      placeholder="Ingrese el nombre del Tenpista"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="transactionDate"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Fecha y Hora</FormLabel>
                  <FormControl>
                    <Input
                      type="datetime-local"
                      {...field}
                      max={new Date().toISOString().slice(0, 16)}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>

          <CardFooter className="flex justify-between">
            <Button type="button" variant="outline" onClick={onCancel}>
              Cancelar
            </Button>
            <Button type="submit">{isNew ? "Crear" : "Actualizar"}</Button>
          </CardFooter>
        </form>
      </Form>
    </Card>
  );
};
