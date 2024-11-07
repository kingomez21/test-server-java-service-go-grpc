using Grpc.Core;
using System.Threading.Tasks;
using Logic;

namespace Logic.Services;

public class TaskServicer : TaskService.TaskServiceBase
{
    private readonly ILogger<TaskServicer> _logger;
    public TaskServicer(ILogger<TaskServicer> logger)
    {
        _logger = logger;
    }

    public override Task<ResponseTask> CreateTask(TaskCreate request, ServerCallContext context)
    {
        return base.CreateTask(request, context);
    }

    public override async Task<ListTask> GetTask(EmptyMessageTask request, ServerCallContext context)
    {
        Console.WriteLine(context);
        var tasks = new ListTask
        {
            Tasks =
            {
                new Task
                {
                    Id = "1",
                    Name = "Example Task",
                    Description = "This is an example task",
                    Date = "2024-01-01",
                    Done = false,
                    
                }
            }
        };
        return await Task<ListTask>.FromResult(tasks);
    }
}
