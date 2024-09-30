INSERT INTO public.vehicle_type
VALUES(1, 'Standard passenger car', 'Car');

INSERT INTO public.vehicle_type
VALUES(2, 'Two-wheeled motor vehicle', 'Motorcycle');

INSERT INTO public.vehicle_type
VALUES(3, 'Large motor vehicle for transporting goods', 'Truck');

INSERT INTO public.vehicle_type
VALUES(4, 'Large vehicle for carrying many passengers', 'Bus');

INSERT INTO public.vehicle_type
VALUES(5, 'Human-powered vehicle with two wheels', 'Bicycle');

commit;

SELECT * FROM public.vehicle_type
ORDER BY type_id ASC

