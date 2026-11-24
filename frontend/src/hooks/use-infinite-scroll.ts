"use client";

import { useEffect, useRef } from "react";

/**
 * Calls `onReachEnd` when the returned ref's element (a marker placed after the list) is within
 * `marginPx` of the viewport. Pass the number of loaded items as `itemCount`: the check runs again
 * after each page, so a tall screen whose marker stays visible keeps loading until it's filled.
 */
export function useInfiniteScroll<T extends Element>(
  onReachEnd: () => void,
  enabled: boolean,
  itemCount: number,
  marginPx = 600,
) {
  const markerRef = useRef<T | null>(null);
  const callbackRef = useRef(onReachEnd);

  useEffect(() => {
    callbackRef.current = onReachEnd;
  }, [onReachEnd]);

  useEffect(() => {
    const marker = markerRef.current;
    if (!enabled || !marker) return;
    // A new observer reports the marker's current position right away, then on every change.
    const observer = new IntersectionObserver(
      (entries) => {
        if (entries.some((entry) => entry.isIntersecting)) callbackRef.current();
      },
      { rootMargin: `0px 0px ${marginPx}px 0px` },
    );
    observer.observe(marker);
    return () => observer.disconnect();
  }, [enabled, marginPx, itemCount]);

  return markerRef;
}
