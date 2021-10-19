Для использования в проекте Docker Compose необходимо:
1. Установить docker на локальную машину по ссылке: https://www.docker.com/products/docker-desktop
2. Запустить docker на локальной машине.
3. Развернуть postgres командой в терминале ИДЕ: docker-compose -f src\main\resources\docker-compose.yml up -d
   ![image](https://user-images.githubusercontent.com/66203643/132941922-be9f532a-3d25-4599-a8fc-62f530bb3190.png)
4. Подключиться из ИДЕ к базе использовав учетные данные:
   username:  postgres,
   password: postgres,
   port: 5433,
   database: gbteam_app,
   url: jdbc:postgresql://localhost:5433/gbteam_app
   ![image](https://user-images.githubusercontent.com/66203643/132941857-c7d2d153-36e7-4ecd-b8ae-09d238137f32.png)
5. Протестировать установленное соединение из ИДЕ.
6. 
   ![image](https://user-images.githubusercontent.com/66203643/132941882-48af1dac-2c13-4e7e-a11d-fc8b464b1d9f.png)

Для обновления/наполнения БД из файла data.sql и schema.sql нужно полностью удалить развернутую в докере БД.
 Для удаления БД воспользуйтесь командой в терминале intellij idea: docker rm -f db_gbteam
![image](https://user-images.githubusercontent.com/66203643/135685853-43e8f1fe-e306-449f-b2b9-b5d5cc42ed92.png)


После этого, разверните БД и наполните ее данными командой в терминале intellij idea: docker-compose -f src\main\resources\docker-compose.yml up -d

![image](https://user-images.githubusercontent.com/66203643/135685896-18d93a5e-a7ed-46b3-94ce-d9dbba7ae767.png)
