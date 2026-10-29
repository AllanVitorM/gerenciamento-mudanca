ALTER TABLE public.usuario
ALTER COLUMN ativo TYPE boolean
USING (ativo = B'1');

ALTER TABLE public.mudanca
ALTER COLUMN ativo TYPE boolean
USING (ativo = B'1')