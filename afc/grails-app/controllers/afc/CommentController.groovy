package afc



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import org.joda.time.LocalDateTime

@Transactional(readOnly = true)
class CommentController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Comment.list(params), model:[commentInstanceCount: Comment.count()]
    }

    def show(Comment commentInstance) {
        respond commentInstance
    }

    def create() {
        respond new Comment(params)
    }

    @Transactional
    def save(Comment commentInstance) {
        if (commentInstance == null) {
            notFound()
            return
        }

        if (commentInstance.hasErrors()) {
            respond commentInstance.errors, view:'create'
            return
        }

        commentInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comment.label', default: 'Comment'), commentInstance.id])
                redirect commentInstance
            }
            '*' { respond commentInstance, [status: CREATED] }
        }
    }

    def edit(Comment commentInstance) {
        respond commentInstance
    }

    @Transactional
    def update(Comment commentInstance) {
        if (commentInstance == null) {
            notFound()
            return
        }

        if (commentInstance.hasErrors()) {
            respond commentInstance.errors, view:'edit'
            return
        }

        commentInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Comment.label', default: 'Comment'), commentInstance.id])
                redirect commentInstance
            }
            '*'{ respond commentInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Comment commentInstance) {

        if (commentInstance == null) {
            notFound()
            return
        }

        commentInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Comment.label', default: 'Comment'), commentInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	def showByMatch(Long id) {
		def match = Match.get(id)
		def matchCommentator = MatchCommentator.findAllByMatch(match)
		[match: match, comments: Comment.findAllByMatch(match), commentators: matchCommentator.commentator]
	}
	
	def addComment(Long matchId, String comment, String authorName) {
		if (matchId != null && comment != null & authorName != null) {
			def match = Match.get(matchId)
			def commentInstance = new Comment(comment: comment, authorName: authorName, timeStamp: new LocalDateTime(), match: match)
			commentInstance.save flush: true
		}
		redirect(action: "showByMatch", params: [id: matchId])
	}
	
	def like(Long id) {
		def commentInstance = Comment.get(id)
		if (commentInstance) {
			commentInstance.likeCount++
			commentInstance.save flush: true
			redirect(action: "showByMatch", params: [id: id])
		}
	}
}
