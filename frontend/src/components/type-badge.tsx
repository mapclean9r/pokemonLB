import { typeColor, typeTextColor } from "@/lib/type-colors";

export function TypeBadge({ type }: { type: string }) {
  return (
    <span
      className="inline-block rounded-full px-3 pt-0.5 pb-px font-display text-sm tracking-wide"
      style={{ backgroundColor: typeColor(type), color: typeTextColor(type) }}
    >
      {type}
    </span>
  );
}
