package ibmro.datanetworksource.services.mappers

import ibmro.common.ModelMapper
import ibmro.datanetworksource.repositories.entities.databaseModels.responseModel.InterviewResponse
import ibmro.datanetworksource.repositories.entities.viewModels.InterviewViewModel
import ibmro.datanetworksource.repositories.entities.viewModels.TestViewModel

class InterviewMapper : ModelMapper<(InterviewResponse), InterviewViewModel> {
    override fun map(input: InterviewResponse): InterviewViewModel =
            InterviewViewModel(
                    id = input.id,
                    tags = TagListMapper().map(input.tags),
                    procentage = input.percentage,
                    grade = input.grade,
                    feedback = input.feedback,
                    date = input.scheduledDateTime,
                    candidate = null,
                    test = input.test.map {
                        TestViewModel(it.questionId, it.answer)
                    },
                    interviewersId = input.interviewersIds
            )
}