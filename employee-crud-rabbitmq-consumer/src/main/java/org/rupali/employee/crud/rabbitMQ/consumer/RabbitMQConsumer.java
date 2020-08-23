package org.rupali.employee.crud.rabbitMQ.consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.rupali.employee.crud.service.EmployeeService;
import org.rupali.employee.crud.dto.EmployeeToEmployeeDTO;
import org.rupali.employee.crud.entity.Employee;
import org.rupali.employee.crud.dao.EmployeeRepository;
import org.rupali.employee.crud.dto.EmployeeDTOToEmployee;
import org.rupali.employee.crud.dto.EmployeeDto;




import java.util.UUID;

@Component
public class RabbitMQConsumer {
	private EmployeeService employeeService;
    private EmployeeDTOToEmployee employeeDTOToEmployee;
    private EmployeeToEmployeeDTO employeeToEmployeeDTO;
	
    @Autowired
    public RabbitMQConsumer(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
   
    @Autowired
    public void setEmployeeDTOToEmployee(EmployeeDTOToEmployee employeeDTOToEmployee) {
        this.employeeDTOToEmployee = employeeDTOToEmployee;
    }

    @Autowired
    public void setEmployeeToEmployeeDTO(EmployeeToEmployeeDTO employeeToEmployeeDTO) {
        this.employeeToEmployeeDTO = employeeToEmployeeDTO;
    }
    

	
	@RabbitListener(queues = "${rupali.rabbitmq.queue}")
	public void recievedMessage(EmployeeDto employeeDto) {
		//Employee emp = employeeDTOToEmployee.convert(employeeDto);
		System.out.println("Recieved Message From RabbitMQ: " + employeeDto);
		Employee ExistingEmployee = employeeService.findEmployeeById(employeeDto.getId());
	    employeeService.updateEmployee(employeeDTOToEmployee.convert(employeeDto), ExistingEmployee);
		
    } 
		
}

