export const Yn = {
  Y: 'Y',
  N: 'N',
} as const;

export type TypeYn = (typeof Yn)[keyof typeof Yn];
