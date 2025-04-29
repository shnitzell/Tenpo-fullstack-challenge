import { TransactionList } from "./pages/TransactionList.tsx";
import { Toaster } from "./components/ui/Toaster.tsx";

import "./App.css";

function App() {
  return (
    <main className="min-h-screen p-4 md:p-8">
      <div className="container mx-auto">
        <h1 className="text-3xl font-bold mb-6">Panel de Transacciones</h1>
        <TransactionList />
        <Toaster />
      </div>
    </main>
  );
}

export default App;
