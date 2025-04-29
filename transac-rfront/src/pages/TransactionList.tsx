import { useState } from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useToast } from "../hooks/use-toast";

import { Button } from "../components/ui/button";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "../components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "../components/ui/table";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "../components/ui/alert-dialog";

import { Edit, Trash2, Plus } from "lucide-react";

import {
  createTransaction,
  deleteTransaction,
  getTransactions,
  Transaction,
  updateTransaction,
} from "../api/transactions.ts";
import { TransactionForm } from "../components/TransactionForm.tsx";

export const TransactionList = () => {
  // States
  const [showForm, setShowForm] = useState(false);
  const [currentTransaction, setCurrentTransaction] =
    useState<Transaction | null>(null);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [transactionToDelete, setTransactionToDelete] = useState<number | null>(
    null
  );

  const { toast } = useToast();

  // Query functions
  const queryClient = useQueryClient();

  const { data, isLoading, isError } = useQuery<Transaction[]>({
    queryKey: ["transactions"],
    queryFn: getTransactions,
  });

  const { mutate: editMutation } = useMutation({
    mutationFn: (transaction: Transaction) => {
      if (transaction.id) {
        return updateTransaction(transaction);
      } else {
        return createTransaction(transaction);
      }
    },
    onSuccess: () => {
      toast({
        title: "Transacción guardada",
        description: "La transacción ha sido guardada correctamente",
      });
      setShowForm(false);
      setCurrentTransaction(null);
      queryClient.invalidateQueries({ queryKey: ["transactions"] });
    },
    onError: (err) => {
      console.error(err);
      toast({
        variant: "destructive",
        title: "Error",
        description: "No se pudo guardar la transacción",
      });
      setShowForm(false);
      setCurrentTransaction(null);
    },
  });

  const { mutate: deleteMutation } = useMutation({
    mutationFn: deleteTransaction,
    onSuccess: () => {
      toast({
        title: "Transacción eliminada",
        description: "La transacción ha sido eliminada correctamente",
      });
      setDeleteDialogOpen(false);
      setTransactionToDelete(null);
      queryClient.invalidateQueries({ queryKey: ["transactions"] });
    },
    onError: (err) => {
      console.error(err);
      toast({
        variant: "destructive",
        title: "Error",
        description: "No se pudo eliminar la transacción",
      });
      setDeleteDialogOpen(false);
      setTransactionToDelete(null);
    },
  });

  const handleEdit = (transaction: Transaction) => {
    setCurrentTransaction(transaction);
    setShowForm(true);
  };

  const handleDelete = (id: number) => {
    setTransactionToDelete(id);
    setDeleteDialogOpen(true);
  };
  const confirmDelete = async () => {
    if (transactionToDelete !== null) {
      deleteMutation(transactionToDelete);
    }
  };
  const handleFormSubmit = editMutation;
  const handleAddNew = () => {
    setCurrentTransaction(null);
    setShowForm(true);
  };

  if (isLoading)
    return (
      <Card>
        <CardHeader>
          <CardTitle>Cargando transacciones...</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex justify-center items-center h-40">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
          </div>
        </CardContent>
      </Card>
    );
  if (isError)
    return (
      <Card>
        <CardHeader>
          <CardTitle className="text-destructive">Error</CardTitle>
        </CardHeader>
        <CardContent>
          <p>No se pudo obtener las transacciones</p>
        </CardContent>
      </Card>
    );

  return (
    <>
      <Card className="mb-6">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle>Transacciones</CardTitle>
          <Button onClick={handleAddNew} disabled={(data?.length ?? 0) >= 100}>
            <Plus className="mr-2 h-4 w-4" /> Nueva Transacción
          </Button>
        </CardHeader>
        <CardContent>
          {data?.length === 0 ? (
            <div className="text-center py-8 text-muted-foreground">
              No hay transacciones registradas. Haga clic en "Nueva Transacción"
              para comenzar.
            </div>
          ) : (
            <div className="overflow-x-auto">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>ID</TableHead>
                    <TableHead>Monto (CLP)</TableHead>
                    <TableHead>Comercio</TableHead>
                    <TableHead>Tenpista</TableHead>
                    <TableHead>Fecha</TableHead>
                    <TableHead className="text-right">Acciones</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {data?.map((transaction) => (
                    <TableRow key={transaction.id}>
                      <TableCell>{transaction.id}</TableCell>
                      <TableCell>
                        ${transaction.amount.toLocaleString()}
                      </TableCell>
                      <TableCell>{transaction.commerce}</TableCell>
                      <TableCell>{transaction.tenpistaName}</TableCell>
                      <TableCell>
                        {new Date(transaction.transactionDate).toLocaleString()}
                      </TableCell>
                      <TableCell className="text-right">
                        <Button
                          variant="ghost"
                          size="icon"
                          onClick={() => handleEdit(transaction)}
                          className="mr-1"
                        >
                          <Edit className="h-4 w-4" />
                          <span className="sr-only">Editar</span>
                        </Button>
                        <Button
                          variant="ghost"
                          size="icon"
                          onClick={() => handleDelete(transaction.id!)}
                        >
                          <Trash2 className="h-4 w-4 text-destructive" />
                          <span className="sr-only">Eliminar</span>
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          )}

          {(data?.length ?? 0) >= 100 && (
            <div className="mt-4 p-2 bg-amber-100 dark:bg-amber-950 text-amber-800 dark:text-amber-200 rounded-md text-sm">
              Has alcanzado el límite máximo de 100 transacciones. Elimina
              algunas para poder agregar más.
            </div>
          )}
        </CardContent>
      </Card>

      {showForm && (
        <TransactionForm
          key={currentTransaction?.id || "new"}
          transaction={currentTransaction}
          onSubmit={handleFormSubmit}
          onCancel={() => {
            setShowForm(false);
            setCurrentTransaction(null);
          }}
        />
      )}

      <AlertDialog open={deleteDialogOpen} onOpenChange={setDeleteDialogOpen}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>¿Estás seguro?</AlertDialogTitle>
            <AlertDialogDescription>
              Esta acción no se puede deshacer. Esto eliminará permanentemente
              la transacción.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction
              onClick={confirmDelete}
              className="bg-destructive text-destructive-foreground hover:bg-destructive/90"
            >
              Eliminar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
};

/*
    <div className="max-w-4xl mx-auto mt-10">
      <h1 className="text-2xl font-bold mb-6">Transactions</h1>
      <table className="min-w-full bg-white shadow-md rounded-lg">
        <thead>
          <tr>
            <th className="py-2">Amount</th>
            <th className="py-2">Commerce</th>
            <th className="py-2">Tenpista</th>
            <th className="py-2">Date</th>
          </tr>
        </thead>
        <tbody>
          {data?.map((transaction) => (
            <tr key={transaction.id} className="text-center border-t">
              <td className="py-2">{transaction.amount}</td>
              <td className="py-2">{transaction.commerce}</td>
              <td className="py-2">{transaction.tenpistaName}</td>
              <td className="py-2">
                {new Date(transaction.transactionDate).toLocaleString()}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>


*/
