import { renderHook, act } from "@testing-library/react";
import "@testing-library/jest-dom";
import { useToast } from "@/hooks/use-toast";

jest.useFakeTimers();

describe("useToast", () => {
  it("should add a toast to state", () => {
    const { result } = renderHook(() => useToast());

    act(() => {
      result.current.toast({
        title: "Hola",
        description: "Mensaje de prueba",
      });
    });

    expect(result.current.toasts.length).toBe(1);
    expect(result.current.toasts[0].title).toBe("Hola");
    expect(result.current.toasts[0].description).toBe("Mensaje de prueba");
    expect(result.current.toasts[0].open).toBe(true);
  });

  it("should dismiss a toast", () => {
    const { result } = renderHook(() => useToast());

    let toastId: string = "";

    act(() => {
      const { id } = result.current.toast({
        title: "A cerrar",
        description: "Mensaje a eliminar",
      });
      toastId = id;
    });

    expect(result.current.toasts[0].open).toBe(true);

    act(() => {
      result.current.dismiss(toastId);
    });

    expect(result.current.toasts[0].open).toBe(false);
  });

  it("should eventually remove the toast after delay", () => {
    const { result } = renderHook(() => useToast());

    let toastId: string = "";

    act(() => {
      const { id } = result.current.toast({
        title: "Remover",
        description: "Auto remove",
      });
      toastId = id;
    });

    act(() => {
      result.current.dismiss(toastId);
    });

    // Simula el paso del tiempo hasta que se borre
    act(() => {
      jest.runAllTimers();
    });

    expect(result.current.toasts.find((t) => t.id === toastId)).toBeUndefined();
  });
});
